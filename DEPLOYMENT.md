# Render Deployment Guide

## Overview

This guide provides step-by-step instructions for deploying the Airtel Professional Inventory Management System to Render.

## Prerequisites

- Render account (free tier available)
- GitHub repository with the project code
- Active internet connection

## Deployment Steps

### 1. Prepare Your Repository

1. **Push all changes to GitHub**:
   ```bash
   git add .
   git commit -m "Ready for Render deployment"
   git push origin main
   ```

2. **Ensure all files are committed**:
   - `render.yaml` - Render service configuration
   - `application-production.properties` - Production settings
   - `Dockerfile` - Container configuration
   - `.dockerignore` - Docker optimization

### 2. Create Render Services

#### A. Web Service (Application)

1. **Login to Render Dashboard**
2. **Click "New +" → "Web Service"**
3. **Connect your GitHub repository**
4. **Configure the service**:
   - **Name**: `airtel-inventory`
   - **Environment**: `Docker`
   - **Branch**: `main`
   - **Root Directory**: `.` (leave empty for root)
   - **Docker Context**: `.` (leave empty for root)
   - **Dockerfile Path**: `./Dockerfile`

5. **Add Environment Variables**:
   ```
   SPRING_PROFILES_ACTIVE=production
   JAVA_VERSION=17
   PORT=10000
   ```

#### B. Database Service

1. **Click "New +" → "PostgreSQL"**
2. **Configure the database**:
   - **Name**: `airtel-inventory-db`
   - **Database Name**: `airtel_inventory`
   - **User**: `airtel_user`
   - **Plan**: Free
   - **Region**: Oregon (or your preferred region)

3. **Note the connection details** (Render will provide these)

#### C. Connect Database to Application

1. **Go back to your web service**
2. **Add database environment variables**:
   ```
   DATABASE_URL=postgresql://airtel_user:password@host:5432/airtel_inventory
   DATABASE_USER=airtel_user
   DATABASE_PASSWORD=your_database_password
   ```

### 3. Configure Application Settings

The application will automatically use the production configuration when `SPRING_PROFILES_ACTIVE=production` is set.

### 4. Deploy and Test

1. **Manual Deploy**: Click "Manual Deploy" on your web service
2. **Wait for deployment** (usually 2-5 minutes)
3. **Test the application**:
   - Navigate to: `https://your-service-name.onrender.com/airtel-inventory/login`
   - Login with: Username `24RP01839`, Password `24RP03971`

### 5. Database Initialization

The first deployment will automatically create the database tables. You may need to:

1. **Access the application** to trigger table creation
2. **Verify all tables are created** in the Render PostgreSQL dashboard
3. **Test basic functionality** (add device, add employee, etc.)

## Configuration Files

### render.yaml
Defines the Render service configuration including web service and database setup.

### application-production.properties
Production-optimized configuration for PostgreSQL database and security settings.

### Dockerfile
Container configuration for building and running the application on Render.

## Environment Variables

| Variable | Description | Value |
|----------|-------------|-------|
| SPRING_PROFILES_ACTIVE | Spring profile to use | `production` |
| PORT | Application port | `10000` |
| DATABASE_URL | PostgreSQL connection string | Provided by Render |
| DATABASE_USER | Database username | `airtel_user` |
| DATABASE_PASSWORD | Database password | Provided by Render |

## Troubleshooting

### Common Issues

1. **Build Fails**:
   - Check Dockerfile syntax
   - Verify all dependencies in pom.xml
   - Ensure Maven wrapper is executable

2. **Database Connection Error**:
   - Verify DATABASE_URL format
   - Check database is running
   - Ensure correct credentials

3. **Application Not Starting**:
   - Check logs in Render dashboard
   - Verify port configuration
   - Ensure health check path is correct

4. **404 Errors**:
   - Verify context path is `/airtel-inventory`
   - Check application.properties configuration

### Health Check

The application includes a health check at `/airtel-inventory/login` which Render uses to verify the service is running.

## Post-Deployment

1. **Update DNS** (if using custom domain)
2. **Configure SSL** (automatically provided by Render)
3. **Monitor performance** using Render dashboard
4. **Set up alerts** for downtime

## Cost Considerations

- **Free Tier**: 750 hours/month, 100GB bandwidth
- **Database**: Free PostgreSQL plan (256MB RAM, 10GB storage)
- **Total Cost**: $0/month for basic usage

## Support

For deployment issues:
1. Check Render documentation: https://render.com/docs
2. Review application logs in Render dashboard
3. Verify environment variables are correctly set

---

**Deployment Status**: Ready for Render deployment  
**Last Updated**: April 2026
